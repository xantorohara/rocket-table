import markdown

with open('README.md', 'r') as file:
    text = file.read()

text = markdown.markdown(text, extensions=[
    'markdown.extensions.tables',
    'markdown.extensions.attr_list',
    'markdown.extensions.fenced_code'
])

text = '''
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Rocket Table</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
            font-size: 16px;
            line-height: 1.5;
            max-width: 900px;
            margin: 50px auto;
        }
        table {
           border-collapse: collapse;
        }
        table td, table th {
            padding: 5px 10px;
            margin: 0;
            box-sizing: border-box;
            border-spacing: 0;
            border: solid 1px #cccccc;
        }
    </style>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/default.min.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
''' + text + '''
</body>
</html>
'''

with open('index.html', 'w') as file:
    text = file.write(text)
