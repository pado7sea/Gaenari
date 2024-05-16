import 'dart:math';

import 'package:flutter/material.dart';

class ImageMove extends StatefulWidget {
  final int id; // id 속성 추가

  // 생성자에서 id를 받아올 수 있도록 변경
  const ImageMove({super.key, required this.id});
  @override
  ImageMoveState createState() => ImageMoveState();
}

class ImageMoveState extends State<ImageMove> with TickerProviderStateMixin {
  // AnimationController를 생성
  late AnimationController _controller;

  // 현재 보여지는 이미지의 경로를 저장하기 위한 변수
  late String _currentImage;
  // 이미지가 움직이는 중인지 여부를 나타내는 변수
  Random random = Random();
  List<double> dx = [10, 50, 100, 150, 200, 250];
  List<double> dy = [110, 140, 210, 280, 350, 370];
  int x = 0;
  int y = 0;
  int act = 0;
  bool left = false;

  @override
  void initState() {
    super.initState();
    // AnimationController를 초기화하고 지속시간을 500밀리초로 설정
    _controller = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    // 초기 이미지를 설정
    act = random.nextInt(6);
    _currentImage = "assets/dogs/1_${act}_${widget.id}.gif";
    x = random.nextInt(6);
    y = random.nextInt(6);
    _moveImage();
  }

  @override
  void dispose() {
    // AnimationController를 dispose하여 메모리 누수를 방지
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      // GestureDetector를 사용하여 이미지를 감싼다
      onTap: () {
        _moveImage();
      },
      child: Stack(
        children: <Widget>[
          AnimatedPositioned(
            left: dx[x],
            top: dy[y],
            duration: Duration(milliseconds: 500),
            child: left
                ? Transform.flip(
                    flipX: true,
                    child: Image.asset(
                      _currentImage,
                      width: 200.0 - (50 - y * 10),
                      fit: BoxFit.cover,
                      filterQuality: FilterQuality.none,
                    ),
                  )
                : Image.asset(
                    _currentImage,
                    width: 200.0 - (50 - y * 10),
                    fit: BoxFit.cover,
                    filterQuality: FilterQuality.none,
                  ),
          ),
        ],
      ),
    );
  }

  // 이미지를 클릭할 때마다 이동시키는 함수
  void _moveImage() {
    setState(() {
      int a = x;
      int b = y;
      x = random.nextInt(6);
      y = random.nextInt(6);
      if ((a - x).abs() + (b - y).abs() > 4 || (b - y).abs() > 2) {
        _currentImage = "assets/dogs/1_${widget.id}.gif";
      } else {
        _currentImage = "assets/dogs/0_${widget.id}.gif";
      }
      left = (a - x) < 0 ? false : true;
    });

    // AnimationController를 이용하여 위치 변경 애니메이션을 실행
    _controller.reset();
    _controller.forward().then((_) {
      setState(() {
        act = random.nextInt(6);
        _currentImage = "assets/dogs/1_${act}_${widget.id}.gif";
      });
    });
  }
}
