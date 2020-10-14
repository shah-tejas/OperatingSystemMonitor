import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import './Graph.css';
import Chart from "react-google-charts";

const Graph = ({ values }) => {

    const [data, setData] = useState([['Time', 'Usage %']]);
    
    useEffect(() => {
        setData([['Time', 'Usage %']]);
        values.forEach(value => {
            const now = Date.now();
            const diffInSeconds = Math.round((now - value.timestamp) / 1000);
            setData(data => [...data, [diffInSeconds, value.percentage]]);
        });
    }, [values]);

    return (
        <div className="chart-container">
            { data.length > 1
                && (
                    <Chart
                        width={'600px'}
                        height={'400px'}
                        chartType="LineChart"
                        // loader={<div>Loading Chart</div>}
                        data={data}
                        options={{
                            hAxis: {
                                title: 'Time (in seconds)',
                                direction: -1,
                            },
                            vAxis: {
                                title: 'Percentage',
                            },
                        }}
                    />
                )
            }
        </div>
    );
};


Graph.propTypes = {
    values: PropTypes.array.isRequired
};


export default Graph;
