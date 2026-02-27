<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>To Do List</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card {
            background: rgba(255,255,255,0.15);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 30px;
            width: 500px;
            color: white;
        }
        .list-group-item {
            background: rgba(255,255,255,0.1);
            color: white;
            border: none;
            margin-bottom: 8px;
            border-radius: 10px;
        }
    </style>
</head>
<body>

<div class="card shadow">
    <h2 class="text-center mb-4">To Do List</h2>

    <div class="input-group mb-3">
        <input type="text" id="newTask" class="form-control" placeholder="Add a new task...">
        <button class="btn btn-light" onclick="addTask()">Add</button>
    </div>

    <ul id="taskList" class="list-group"></ul>
</div>

<script>
    const API = "<%= request.getContextPath() %>/api/tasks";

    function loadTasks() {
        fetch(API)
            .then(res => res.json())
            .then(data => {
                const list = document.getElementById("taskList");
                list.innerHTML = "";

                data.forEach(task => {
                    const li = document.createElement("li");
                    li.className = "list-group-item d-flex justify-content-between align-items-center";

                    li.innerHTML = `
                        <div>
                            <input type="checkbox" ${task.completed ? "checked" : ""}
                                   onchange="toggleTask(${task.id}, this.checked)">
                            <span id="title-${task.id}"
                                  style="${task.completed ? 'text-decoration: line-through;' : ''}">
                                ${task.title}
                            </span>
                        </div>
                        <div>
                            <i class="bi bi-pencil-square text-warning me-2"
                               style="cursor:pointer"
                               onclick="editTask(${task.id})"></i>
                            <i class="bi bi-trash text-danger"
                               style="cursor:pointer"
                               onclick="deleteTask(${task.id})"></i>
                        </div>
                    `;
                    list.appendChild(li);
                });
            });
    }

    function addTask() {
        const input = document.getElementById("newTask");
        if (!input.value.trim()) return;

        fetch(API, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({title: input.value, completed: false})
        }).then(() => {
            input.value = "";
            loadTasks();
        });
    }

    function toggleTask(id, completed) {
        const title = document.getElementById("title-" + id).innerText;

        fetch(API + "/" + id, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({title: title, completed: completed})
        }).then(loadTasks);
    }

    function deleteTask(id) {
        fetch(API + "/" + id, { method: "DELETE" })
            .then(loadTasks);
    }

    function editTask(id) {
        const span = document.getElementById("title-" + id);
        const newTitle = prompt("Edit task:", span.innerText);
        if (!newTitle) return;

        fetch(API + "/" + id, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                title: newTitle,
                completed: span.style.textDecoration === "line-through"
            })
        }).then(loadTasks);
    }

    loadTasks();
</script>

</body>
</html>