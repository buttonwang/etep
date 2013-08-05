package com.ambow.core.security.jcaptcha;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JCaptchaServiceProxyImpl {
    protected static final Log log = LogFactory
            .getLog(JCaptchaServiceProxyImpl.class);

    private CaptchaService jcaptchaService;

    public boolean validateReponseForId(String id, Object response) {
        log.debug("validating captcha response");

        try {
            boolean isHuman = jcaptchaService.validateResponseForID(id,
                    response).booleanValue();
            if (isHuman) {
                log.debug("captcha passed");
            } else {
                log.warn("captcha failed");
            }
            return isHuman;

        } catch (CaptchaServiceException cse) {
            // fixes known bug in JCaptcha
            log.warn("captcha validation failed due to exception", cse);
            return false;
        }
    }

    public void setJcaptchaService(CaptchaService jcaptchaService) {
        this.jcaptchaService = jcaptchaService;
    }
}
