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

package sample.messages.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
public class InboxPage {

	private WebDriver driver;

	private WebElement deleted;

	public InboxPage(WebDriver webDriver) {
		this.driver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public static <T> T to(WebDriver driver, Class<T> page) {
		driver.get("http://localhost:8080/messages/inbox");
		return (T) PageFactory.initElements(driver, page);
	}

	public InboxPage assertDeleteSuccess() {
		assertThat(this.deleted.getText()).isEqualTo("Your message has been deleted");
		return this;
	}

	public InboxPage assertAt() {
		assertThat(this.driver.getTitle()).endsWith("- Inbox");
		return this;
	}

	public List<Message> messages() {
		List<Message> messages = new ArrayList<>();
		List<WebElement> rows = this.driver.findElements(By.cssSelector("#messages tbody tr"));
		for(WebElement row : rows) {
			messages.add(new Message(row));
		}
		return messages;
	}

	public class Message {
		@FindBy(xpath = ".//td[1]")
		WebElement from;

		@FindBy(xpath = ".//td[2]")
		WebElement text;

		@FindBy(css = "button[type=submit]")
		WebElement delete;

		public Message(SearchContext context) {
			PageFactory.initElements(new DefaultElementLocatorFactory(context), this);
		}

		public String getFrom() {
			return this.from.getText();
		}

		public String getText() {
			return this.text.getText();
		}

		public InboxPage delete() {
			this.delete.click();
			return new InboxPage(InboxPage.this.driver);
		}
	}
}
