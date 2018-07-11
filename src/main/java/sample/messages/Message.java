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

import sample.users.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Rob Winch
 */
@Entity
public class Message {
	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty(message = "Message is required.")
	private String text;

	@OneToOne
	@NotNull
	private User to;

	@OneToOne
	@NotNull
	private User from;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getTo() {
		return this.to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public User getFrom() {
		return this.from;
	}

	public void setFrom(User from) {
		this.from = from;
	}
}
