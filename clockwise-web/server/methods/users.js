import {Meteor} from 'meteor/meteor';
import { check } from 'meteor/check';
import { HTTP } from 'meteor/http';

export default function() {
	Meteor.methods({
		'user.login'(login, password) {
			check(login, String);
			check(password, String);

			var responseTk = HTTP.call('POST', 'https://localhost:8080/api/authenticate', {
				data: {
					"username": login,
					"password": password
				}
			});

			console.log(responseTk.data.token);

			return responseTk.data.token;
		},

		'user.info'(username, token) {
			check(username, String);
			check(token, String);

			let response = HTTP.call('GET', 'https://localhost:8080/api/users?username='+username, {
				headers: {
					"Clockwise-Token": token
				}
			});

			return response;
		},

		'user.register'(username, email, password) {
			check(username, String);
			check(email, String);
			check(password, String);

			var responseToken = HTTP.call('POST', 'https://localhost:8080/api/users', {
				data: {
					"username": username,
					"email"	  : email,
					"password": password,
					"role"	  : "ROLE_TEAM_LEADER"
				}
			});

			return responseToken;
		}
	});
}