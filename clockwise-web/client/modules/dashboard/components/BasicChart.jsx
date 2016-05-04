import React from 'react';
import { ScatterChart } from 'rd3';

class BasicChart extends React.Component {
    render() {
        var scatterData = [
            {
                name: "series1",
                values: [{ 
                    x: 0, 
                    y: 20 
                }, { 
                    x: 24, 
                    y: 10 
                }, {
                    x: 34,
                    y: 81  
                }, { 
                    x: 70, 
                    y: 82 
                },{x: 64, y: 92},{x: 39, y: 12},{x: 40, y: 61},{x: 43, y: 2},{x: 99, y: 62},{x: 91, y: 59},{x: 24, y: 62},{x: 4, y: 1},{x: 49, y: 38},{x: 1, y: 28},{x: 24, y: 43},{x: 75, y: 77},{x: 25, y: 15},{x: 19, y: 68},{x: 24, y: 46},{x: 81, y: 31},{x: 27, y: 90},{x: 55, y: 13},{x: 70, y: 1},{x: 62, y: 69},{x: 1, y: 63},{x: 1, y: 37},{x: 80, y: 78},{x: 43, y: 75},{x: 79, y: 10},{x: 73, y: 12},{x: 13, y: 69},{x: 16, y: 81},{x: 99, y: 66},{x: 34, y: 100},{x: 65, y: 77},{x: 33, y: 97},{x: 90, y: 44},{x: 53, y: 5},{x: 36, y: 65},{x: 38, y: 74},{x: 55, y: 13},{x: 5, y: 93},{x: 59, y: 30},{x: 50, y: 77},{x: 9, y: 26},{x: 10, y: 32},{x: 13, y: 1},{x: 97, y: 15},{x: 82, y: 90},{x: 95, y: 38},{x: 77, y: 28},{x: 31, y: 4},{x: 8, y: 33},{x: 42, y: 38},
                 { 
                    x: 76, 
                    y: 82 
                } ]
            }
        ];

        return (
              <ScatterChart
                data={scatterData}
                width={500}
                height={400}
                title="Scatter Chart"
              />
        );
    }
}

export default BasicChart;