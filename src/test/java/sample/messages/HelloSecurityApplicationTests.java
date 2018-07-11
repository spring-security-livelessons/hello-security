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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sample.messages.webdriver.InboxPage;
import sample.messages.webdriver.IndexPage;

import java.util.List;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class HelloSecurityApplicationTests {
	private WebDriver driver;

	@Autowired
	public void setMockMvc(MockMvc mockMvc) {
		WebConnectionHtmlUnitDriver delegate = new WebConnectionHtmlUnitDriver();
//		delegate.setExecutor(new DelegatingSecurityContextExecutor(
//				Executors.newCachedThreadPool()));
		this.driver = MockMvcHtmlUnitDriverBuilder.mockMvcSetup(mockMvc)
				.withDelegate(delegate)
				.build();
	}

	@Test
	public void indexRedirects() {
		InboxPage inbox = IndexPage.to(this.driver, InboxPage.class);
		inbox.assertAt();
	}

	@Test
	public void inboxLoads() {
		InboxPage inbox = InboxPage.to(this.driver, InboxPage.class);
		inbox.assertAt();
	}

	@Test
	public void messages() {
		InboxPage inbox = InboxPage.to(this.driver, InboxPage.class);
		List<InboxPage.Message> messages = inbox.messages();
		assertThat(messages).hasSize(4);
		InboxPage.Message message0 = messages.get(0);
		assertThat(message0.getFrom()).isEqualTo("josh@example.com");
		assertThat(message0.getText()).isEqualTo("This message is for Rob");
	}

	@Test
	@DirtiesContext
	public void deleteWorks() {
		InboxPage inbox = InboxPage.to(this.driver, InboxPage.class);
		InboxPage.Message toDelete = inbox.messages().get(0);

		inbox = toDelete.delete();
		inbox.assertDeleteSuccess();
		InboxPage.Message message0 = inbox.messages().get(0);

		assertThat(inbox.messages()).hasSize(3);
		assertThat(message0.getText()).isNotEqualTo("This message is for Rob");
	}

	static class Patch extends WebConnectionHtmlUnitDriver {

	}
}
