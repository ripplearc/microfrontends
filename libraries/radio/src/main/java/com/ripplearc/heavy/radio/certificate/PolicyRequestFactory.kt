package com.ripplearc.heavy.radio.certificate

import com.amazonaws.services.iot.model.AttachPolicyRequest
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Named

/**
 * The Class PolicyRequestFactory creates a request
 * that attaches an Iot policy to the certificate.
 */
@Reusable
class PolicyRequestFactory @Inject constructor(
    @param:Named("IotPolicyName") private val policyName: String
) {
    /**
     * Create the request that attaches the the policy with policyName
     * to the certificate.
     * @param certificateArn Arn of the the certificate in AWS
     */
    fun createPolicyRequest(certificateArn: String): AttachPolicyRequest =
        AttachPolicyRequest()
            .withPolicyName(policyName)
            .withTarget(certificateArn)
}