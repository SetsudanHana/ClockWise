import React from 'react';
import {mount} from 'react-mounter';

import {Grid} from 'react-bootstrap';

import MainLayout from './components/MainLayout.jsx';
import Login from '../users/containers/Login.js';
import Register from '../users/containers/Register.js';
import BasicChart from '../dashboard/containers/BasicChart.js';
import NavBar from './components/NavBar.jsx';

export default function (injectDeps, {FlowRouter}) {
    const MainLayoutCtx = injectDeps(MainLayout);

    FlowRouter.route('/', {
        name: 'main.page',
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
            mount(MainLayoutCtx, {
                content: () => (<div>
                                <NavBar/>
                                <div className="dashboard">
                                    <BasicChart/>
                                </div>
                            </div>)
            });
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