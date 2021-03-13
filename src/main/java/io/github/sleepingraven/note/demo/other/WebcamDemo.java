package io.github.sleepingraven.note.demo.other;

/**
 * 来源：网络
 *
 * @author 10132
 */
public class WebcamDemo {
    
    /* --------- dependencies required ---------
     <dependency>
        <groupId>com.github.sarxos</groupId>
        <artifactId>webcam-capture</artifactId>
        <version>0.3.12</version>
    </dependency>

    <dependency>
        <groupId>com.nativelibs4java</groupId>
        <artifactId>bridj</artifactId>
        <version>0.7.0</version>
    </dependency>
     */
    
    /* --------- comment this line ---------
    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        
        JFrame window = new JFrame("Test webcam panel");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
    /**/
}

/* webcam-demo.html             */
// double click the blank space to select all
/*<!DOCTYPE html>
<html lang='en'>
<head>
<meta charset='UTF-8'>
<title>Title</title>
</head>
<body>

<button onclick='openMedia()'>开启摄像头</button>
<video id='video' width='500px' height='500px' autoplay='autoplay'></video>
<canvas id='canvas' width='500px' height='500px'></canvas>
<button onclick='takePhoto()'>拍照</button>
<img id='imgTag' alt='imgTag'>
<button onclick='closeMedia()'>关闭摄像头</button>

<script>
	let mediaStreamTrack = null; // 视频对象(全局)
            function openMedia() {
            let constraints = {
            video : { width : 500, height : 500 }, audio : true
            };
            //获得video摄像头
            let video = document.getElementById('video');
            let promise = navigator.mediaDevices.getUserMedia(constraints);
            promise.then((mediaStream) => {
            mediaStreamTrack = typeof mediaStream.stop === 'function' ? mediaStream : mediaStream.getTracks()[1];
            video.srcObject = mediaStream;
            video.play();
            });
            }
            
            // 拍照
            function takePhoto() {
            //获得Canvas对象
            let video = document.getElementById('video');
            let canvas = document.getElementById('canvas');
            let ctx = canvas.getContext('2d');
            ctx.drawImage(video, 0, 0, 500, 500);
            // toDataURL  —  可传入'image/png'—默认, 'image/jpeg'
            let img = document.getElementById('canvas').toDataURL();
            // 这里的img就是得到的图片
            console.log('img—–', img);
            document.getElementById('imgTag').src = img;
            }
            
            // 关闭摄像头
            function closeMedia() {
            mediaStreamTrack.stop();
            }
</script>

</body>
</html>
*/
