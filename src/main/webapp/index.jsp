<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hello World Refined</title>
    <style>
        /* This centers everything on the screen */
        body {
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: white;
        }

        /* Adding some "eye candy" to the text */
        h2 {
            font-size: 4rem;
            text-shadow: 2px 4px 10px rgba(0, 0, 0, 0.3);
            animation: fadeIn 2s ease-in-out;
        }

        /* A simple entry animation */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
    <h2>Hello World!</h2>
</body>
</html>