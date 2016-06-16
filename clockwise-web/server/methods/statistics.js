import {Meteor} from 'meteor/meteor';
import { check } from 'meteor/check';
import { HTTP } from 'meteor/http';

export default function() {
	Meteor.methods({
		'statistics.user'(userId, token) {
			check(userId, Number);
			check(token, String);

			let statistics = HTTP.call('GET', 'https://peaceful-garden-30857.herokuapp.com/api/users/' + userId + '/statistics', {
				headers: {
					"Clockwise-Token": token
				}
			});

			return statistics;
		},

		'statistics.screenshots.user.all'(userId) {
			check(userId, Number);

			let screenshots = HTTP.get('https://peaceful-garden-30857.herokuapp.com/api/screenshot/all/' + userId, {});

			return screenshots;
		}
	});
}