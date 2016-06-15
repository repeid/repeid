/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.testsuite.forms;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.keycloak.events.Details;
import org.keycloak.models.OTPPolicy;
import org.keycloak.models.utils.HmacOTP;
import org.keycloak.testsuite.AssertEvents;
import org.keycloak.testsuite.pages.AppPage;
import org.keycloak.testsuite.pages.AppPage.RequestType;
import org.keycloak.testsuite.pages.LoginPage;
import org.keycloak.testsuite.pages.LoginTotpPage;

import java.net.MalformedURLException;
import org.jboss.arquillian.graphene.page.Page;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testsuite.TestRealmKeycloakTest;
import org.keycloak.testsuite.util.GreenMailRule;
import org.keycloak.testsuite.util.UserBuilder;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 * @author Stan Silvert ssilvert@redhat.com (C) 2016 Red Hat Inc.
 */
public class LoginHotpTest extends TestRealmKeycloakTest {

    public static OTPPolicy policy;

    @Override
    public void configureTestRealm(RealmRepresentation testRealm) {
        UserRepresentation user = findUserInRealmRep(testRealm, "test-user@localhost");
        UserBuilder.edit(user)
                   .hotpSecret("hotpSecret")
                   .otpEnabled();
    }

    @Rule
    public AssertEvents events = new AssertEvents(this);

    @Rule
    public GreenMailRule greenMail = new GreenMailRule();

    @Page
    protected AppPage appPage;

    @Page
    protected LoginPage loginPage;

    @Page
    protected LoginTotpPage loginTotpPage;

    private HmacOTP otp; // = new HmacOTP(policy.getDigits(), policy.getAlgorithm(), policy.getLookAheadWindow());

    private int lifespan;

    private static int counter = 0;

    @Before
    public void before() throws MalformedURLException {
        RealmRepresentation testRealm = testRealm().toRepresentation();
        testRealm.setOtpPolicyType(UserCredentialModel.HOTP);
        testRealm.setOtpPolicyLookAheadWindow(2);
        testRealm().update(testRealm);

        policy = new OTPPolicy();
        policy.setAlgorithm(testRealm.getOtpPolicyAlgorithm());
        policy.setDigits(testRealm.getOtpPolicyDigits());
        policy.setInitialCounter(testRealm.getOtpPolicyInitialCounter());
        policy.setLookAheadWindow(testRealm.getOtpPolicyLookAheadWindow());
        policy.setPeriod(testRealm.getOtpPolicyLookAheadWindow());
        policy.setType(testRealm.getOtpPolicyType());

        otp = new HmacOTP(policy.getDigits(), policy.getAlgorithm(), policy.getLookAheadWindow());
    }

    @Test
    public void loginWithHotpFailure() throws Exception {
        loginPage.open();
        loginPage.login("test-user@localhost", "password");

        Assert.assertTrue(loginTotpPage.isCurrent());

        loginTotpPage.login("123456");
        loginTotpPage.assertCurrent();
        Assert.assertEquals("Invalid authenticator code.", loginPage.getError());

        //loginPage.assertCurrent();  // Invalid authenticator code.
        //Assert.assertEquals("Invalid username or password.", loginPage.getError());

        events.expectLogin().error("invalid_user_credentials").session((String) null)
                .removeDetail(Details.CONSENT)
                .assertEvent();
    }

    @Test
    public void loginWithMissingHotp() throws Exception {
        loginPage.open();
        loginPage.login("test-user@localhost", "password");

        Assert.assertTrue(loginTotpPage.isCurrent());

        loginTotpPage.login(null);
        loginTotpPage.assertCurrent();
        Assert.assertEquals("Invalid authenticator code.", loginPage.getError());

        //loginPage.assertCurrent();  // Invalid authenticator code.
        //Assert.assertEquals("Invalid username or password.", loginPage.getError());

        events.expectLogin().error("invalid_user_credentials").session((String) null)
                .removeDetail(Details.CONSENT)
                .assertEvent();
    }

    @Test
    public void loginWithHotpSuccess() throws Exception {
        loginPage.open();
        loginPage.login("test-user@localhost", "password");

        Assert.assertTrue(loginTotpPage.isCurrent());

        loginTotpPage.login(otp.generateHOTP("hotpSecret", counter++));

        Assert.assertEquals(RequestType.AUTH_RESPONSE, appPage.getRequestType());

        events.expectLogin().assertEvent();
    }

    @Test
    public void loginWithHotpInvalidPassword() throws Exception {
        loginPage.open();
        loginPage.login("test-user@localhost", "invalid");

        Assert.assertTrue(loginPage.isCurrent());

        Assert.assertEquals("Invalid username or password.", loginPage.getError());

        events.expectLogin().error("invalid_user_credentials").session((String) null)
                .removeDetail(Details.CONSENT)
                .assertEvent();
    }
}
