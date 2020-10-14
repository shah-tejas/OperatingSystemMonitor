import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import './OSUsage.css';
import { API_BASE_URL } from '../../utils/constants';
import { Graph } from '..';

const OSUsage = ({ start, cpu }) => {

    const [cpuValues, setCpuValues] = useState([]);
    const [memoryValues, setMemoryValues] = useState([]);
    const [intervalId, setIntervalId] = useState();

    const fetchCPUUsage = async () => {
        const resp = await fetch(`${API_BASE_URL}/usage/cpu`);
        const data = await resp.json();
        setCpuValues(data);
    }

    const fetchMemoryUsage = async () => {
        const resp = await fetch(`${API_BASE_URL}/usage/memory`);
        const data = await resp.json();
        setMemoryValues(data);
    }

    useEffect(() => {
        if(start) {
            const interval = setInterval(() => {
                fetchCPUUsage();
                fetchMemoryUsage();
            }, 5000);
            setIntervalId(interval);
        }
    }, [start, cpu]);

    useEffect(() => {
        if(!start 
            && cpuValues.length > 1
            && memoryValues.length > 1) {
            setCpuValues([]);
            setMemoryValues([]);
        }
        if(intervalId && !start) {
            clearInterval(intervalId);
        }
    })

    if(!start) {
        return (
            <p>Press start to start the monitor!</p>
        )
    }

    if(start && cpuValues.length < 1) {
        return (
            <p>Loading chart...</p>
        )
    }

    return (
        <div>
            <Graph values={cpu ? cpuValues : memoryValues} />
        </div>
    );
}

OSUsage.propTypes = {
    start: PropTypes.bool
}

export default OSUsage;
