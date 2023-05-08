package com.ripplearc.heavy.radio.messaging

import com.ripplearc.heavy.radio.parser.model.Message
import com.ripplearc.heavy.radio.parser.model.Request
import com.ripplearc.heavy.radio.parser.model.RequestType
import com.ripplearc.heavy.toolbelt.constants.Emoji
import com.ripplearc.heavy.toolbelt.rx.log
import io.reactivex.Flowable

/**
 * An extension of the Flowable of type Message. It dispatches the task to the responders
 * based on the RequestType.
 * @param responders A map between RequestType to Flowable that responds to the request.
 * @return The Json response sent back.
 */
fun Flowable<Message>.process(responders: Map<RequestType, (String?) -> Flowable<String>>): Flowable<String> {
	val messages = distinct { message -> message.timestamp }
		.map { it.requests.toList() }
		.flatMapIterable { it }
		.log(Emoji.Victory)
		.share()

	val nonTogglableCommands = messages
		.filter { !isToggleCommandType(it) }
		.flatMap { (type, request) ->
			responders[type]?.let { it("") }
				?.onErrorResumeNext(Flowable.empty())
				?: Flowable.empty()
		}

	val togglableCommands = messages
		.filter { isToggleCommandType(it) }
		.switchMap { (type, request) ->
			if (request.toggle != false) {
				responders[type]?.let { it(request.activity ?: "") }
					?.onErrorResumeNext(Flowable.empty())
					?: Flowable.empty()
			} else {
				Flowable.empty()
			}
		}

	return nonTogglableCommands.mergeWith(togglableCommands)
}

private fun isToggleCommandType(
	message: Pair<RequestType, Request>
): Boolean = listOf(RequestType.ToggleCollectSensorData).contains(message.first)
