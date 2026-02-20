<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hello World Refined</title>
    <style>
        /* Layout: full-height column with centered main content and footer */
        body {
            margin: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: white;
        }

        .content {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
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
    <div class="content">
        <h2>Hello World!</h2>
    </div>

    <footer>
        <div style="width:100%;max-width:1000px;margin:0 auto;padding:12px 20px;text-align:center;opacity:0.95;">
            <small>&copy; 2026 My Webapp — Built with care. | <a href="#" style="color:rgba(255,255,255,0.85);text-decoration:underline;">Privacy</a> | <a href="#" style="color:rgba(255,255,255,0.85);text-decoration:underline;">Terms</a></small>
        </div>
    </footer>
</body>
</html>