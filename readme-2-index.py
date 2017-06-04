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
    <!-- Yandex.Metrika counter --> <script type="text/javascript"> (function (d, w, c) { (w[c] = w[c] || []).push(function() { try { w.yaCounter25278200 = new Ya.Metrika({ id:25278200, clickmap:true, trackLinks:true, accurateTrackBounce:true }); } catch(e) { } }); var n = d.getElementsByTagName("script")[0], s = d.createElement("script"), f = function () { n.parentNode.insertBefore(s, n); }; s.type = "text/javascript"; s.async = true; s.src = "https://mc.yandex.ru/metrika/watch.js"; if (w.opera == "[object Opera]") { d.addEventListener("DOMContentLoaded", f, false); } else { f(); } })(document, window, "yandex_metrika_callbacks"); </script> <noscript><div><img src="https://mc.yandex.ru/watch/25278200" style="position:absolute; left:-9999px;" alt="" /></div></noscript> <!-- /Yandex.Metrika counter -->
</head>
<body>
''' + text + '''
</body>
</html>
'''

with open('index.html', 'w') as file:
    text = file.write(text)
