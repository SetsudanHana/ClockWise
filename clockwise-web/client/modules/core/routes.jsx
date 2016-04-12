import React from 'react';
import {mount} from 'react-mounter';

import MainLayout from './components/main_layout.jsx';
import Login from '../users/containers/Login.js';

export default function (injectDeps, {FlowRouter}) {
	const MainLayoutCtx = injectDeps(MainLayout);

	FlowRouter.route('/', {
		name: 'main.page',
		action() {
			mount(MainLayoutCtx, {
				content: () => (<Login />)
			});
		}
	});
}