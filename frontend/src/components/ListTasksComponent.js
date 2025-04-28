import React from 'react'

function ListTasksComponent() {
    const dummyData = [
        {
            "id": 1,
            "taskGroup": "Group1",
            "taskName": "ITR",
            "taskCheckList": "checkList",
            "advanceIntimation": "",
            "remarks": "check before approve",
            "taskType": "M",
            "taskAssignedTo": "Mahesh",
            "taskStatus": "created"
        },
        {
            "id": 2,
            "taskGroup": "Group1",
            "taskName": "ITR",
            "taskCheckList": "checkList",
            "advanceIntimation": "",
            "remarks": "check before approve",
            "taskType": "M",
            "taskAssignedTo": "Mahesh",
            "taskStatus": "created"
        }
    ]
    return (
        <div className='container'>
            <h2>Empoyee Tasks</h2>
            <table className='table table-stripped table-bordered'>
                <thead>
                <tr>
                    <th>Task Id</th>
                    <th>Task Group</th>
                    <th>Task Name</th>
                    <th>TaskCheckList</th>
                    <th>AdvanceIntimation</th>
                    <th>Remarks</th>
                    <th>TaskType</th>
                    <th>TaskAssignedTo</th>
                    <th>TaskStatus</th>
                </tr>
                </thead>
                <tbody>
                {
                    dummyData.map(task =>
                        <tr key={task.id}>
                            <td>{task.id}</td>
                            <td>{task.taskGroup}</td>
                            <td>{task.taskName}</td>
                            <td>{task.taskCheckList}</td>
                            <td>{task.advanceIntimation}</td>
                            <td>{task.remarks}</td>
                            <td>{task.taskType}</td>
                            <td>{task.taskAssignedTo}</td>
                            <td>{task.taskStatus}</td>
                        </tr>)
                }

                </tbody>
            </table>

        </div>
    )
}

export default ListTasksComponent