<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>

<body>
<script>
    function rgb(red, green, blue) {
        var decColor = 0x1000000 + blue + 0x100 * green + 0x10000 * red;
        return '#' + decColor.toString(16).substr(1);
    }

    function changeBackground(red, green, blue) {
        document.body.style.backgroundColor = rgb(red, green, blue);
    }


    var rgbArray =${rgb};
    console.log('rgb array = ', rgbArray);

    var index = 0;

    function blink() {
        var value = rgbArray[index];
        console.log('index = ', index, 'rgb value = ', value);
        var red = value[0];
        var green = value[1];
        var blue = value[2];
        console.log('red = ', red, 'green = ', green, ' blue = ', blue);
        changeBackground(red, green, blue);
        if (index === rgbArray.length - 1) {
            index = 0;
        } else {
            index++;
        }
    }

    setInterval(blink, 1000);
</script>

</body>
</html>