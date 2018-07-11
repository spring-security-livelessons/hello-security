/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.messages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sample.messages.webdriver.InboxPage;
import sample.messages.webdriver.LoginPage;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class HelloSecurityTests {
	@Autowired
	private WebDriver driver;

	@Autowired
	private SecurityProperties securityProperties;

	@Test
	public void inboxRequiresLogin() {
		LoginPage login = InboxPage.to(this.driver, LoginPage.class);
		login.assertAt();
	}

	@Test
	public void loginFailure() {
		LoginPage login = InboxPage.to(this.driver, LoginPage.class);
		login.form()
			.username("user")
			.password("invalid")
			.login(LoginPage.class)
			.assertAt();
	}

	@Test
	public void loginSuccess() {
		LoginPage login = InboxPage.to(this.driver, LoginPage.class);
		login.form()
				.username(this.securityProperties.getUser().getName())
				.password(this.securityProperties.getUser().getPassword())
				.login(InboxPage.class)
				.assertAt();
	}
}
