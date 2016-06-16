import React from 'react';
import { LineChart } from 'rd3';

import { Meteor } from 'meteor/meteor';

class BasicChart extends React.Component {
    render() {
        var lineData = [
              { 
                name: 'series1',
                values: [{ 
                    x: 1467064800000, 
                    y: 34 
                }, { 
                    x: 1467151200000, 
                    y: 30 
                }, { 
                    x: 1467324000000, 
                    y: 25
                }, { 
                    x: 1467324000000, 
                    y: 29 
                }, { 
                    x: 1467410400000, 
                    y: 42 
                }, { 
                    x: 1467496800000, 
                    y: 32 
                }, { 
                    x: 1467583200000, 
                    y: 26 
                } ]
              }
            ];

        return (
              <LineChart
                  data={lineData}
                  width='100%'
                  height={400}
                  viewBoxObject={{
                    x: 0,
                    y: 0,
                    width: 500,
                    height: 400
                  }}
                  yAxisLabel="Mouse Clicked"
                  xAxisLabel="days"
                  domain={{x: [,], y: [10,50]}}
                  gridHorizontal={true}
                  xAccessor={(d)=> {
                      return new Date(d.x);
                    }     
                  }
                  yAccessor={(d)=>d.y}
                  xAxisTickInterval={{unit: 'day', interval: 1}}
                />
        );
    }
}

export default BasicChart;