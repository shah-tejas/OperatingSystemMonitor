import React, { useState } from 'react';
import './OperatingSystemMonitor.css';
import { API_BASE_URL } from '../../utils/constants';
import { OSUsage } from '..';

const OperatingSystemMonitor = () => {
    const [cpu, setCpu] = useState(true);
    const [start, setStart] = useState(false);

    const handleRadioClick = (e) => {
        setCpu(e.target.value === 'cpu');
    }

    const buttonHandler = () => {
        const startMonitor = !start;
        setStart(startMonitor);

        const changeMonitorStatus = async (startMonitor) => {
            await fetch(`${API_BASE_URL}/monitorstatus/${startMonitor ? 'start' : 'stop'}`, { method: 'POST'});
        }

        changeMonitorStatus(startMonitor);
    }

    return (
        <div>
            <div>
                <button onClick={buttonHandler}>{start ? 'Stop' : 'Start'}</button>
            </div>
            <div>
                <input type="radio" name="metric" value="cpu" checked={cpu} onChange={handleRadioClick} />
                <label htmlFor="cpu">CPU</label>
                <input type="radio" name="metric" value="memory" checked={!cpu} onChange={handleRadioClick} />
                <label htmlFor="memory">Memory</label>
            </div>
            <div>
                <OSUsage start={start} cpu={cpu} />
            </div>
        </div>
    );
}

export default OperatingSystemMonitor;
