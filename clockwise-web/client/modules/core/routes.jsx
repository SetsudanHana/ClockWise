import React from 'react';
import {mount} from 'react-mounter';

import {Grid} from 'react-bootstrap';

import MainLayout from './components/MainLayout.jsx';
import Login from '../users/containers/Login.js';
import Register from '../users/containers/Register.js';
import MainDashboard from '../dashboard/components/Dashboard.jsx';
import DashboardStatistics from '../dashboard/components/DashboardStatistics.jsx';
import DashboardScreenshots from '../dashboard/components/DashboardScreenshots.jsx';

export default function (injectDeps, {FlowRouter}) {
    const MainLayoutCtx = injectDeps(MainLayout);

    FlowRouter.route('/', {
        name: 'main.page',
        action() {
            if (Session.get('user_token')) {
                FlowRouter.go('/dashboard');
            } else {
                FlowRouter.go('/login');
            }
        }
    });

    FlowRouter.route('/login', {
        name: 'main.login',
        action() {
            mount(MainLayoutCtx, {
                content: () => (<div className="wrapper">
                    <Grid>
                        <Login />
                    </Grid>
                </div>)
            });
        }
    });

    FlowRouter.route('/dashboard', {
        name: 'main.dashboard',
        action() {
            if (Session.get('user_token')) {
                mount(MainLayoutCtx, {
                    content: () => (<div>
                        <MainDashboard />
                    </div>)
                });
            } else {
                FlowRouter.go('/login')
            }
        }
    });

    FlowRouter.route('/statistics', {
        name: 'dashboard.statistics',
        action() {
            if (Session.get('user_token')) {
                mount(MainLayoutCtx, {
                    content: () => (<div>
                        <DashboardStatistics />
                    </div>)
                });
            } else {
                FlowRouter.go('/login');
            }
        }
    });

    FlowRouter.route('/screenshots', {
        name: 'dashboard.screenshots',
        action() {
            if (Session.get('user_token')) {
                mount(MainLayoutCtx, {
                    content: () => (<div>
                        <DashboardScreenshots />
                    </div>)
                });
            } else {
                FlowRouter.go('/login');
            }
        }
    });

    FlowRouter.route('/register', {
        name: 'main.register',
        action() {
            mount(MainLayoutCtx, {
                content: () => (<div className="wrapper">
                    <Grid>
                        <Register />
                    </Grid>
                </div>)
            });
        }
    });
}