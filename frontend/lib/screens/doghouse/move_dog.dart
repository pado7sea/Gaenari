import 'dart:math';

import 'package:flutter/material.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/bubbel.dart';

class ImageMove extends StatefulWidget {
  final int id; // id 속성 추가
  final String tier;
  final String name;
  final bool myhome;

  // 생성자에서 id를 받아올 수 있도록 변경
  const ImageMove(
      {super.key,
      required this.id,
      required this.tier,
      required this.name,
      this.myhome = true});
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
  List<double> dx = [10, 50, 100, 150, 200, 230];
  List<double> dy = [110, 140, 210, 280, 350, 370];
  int x = 0;
  int y = 0;
  int z = 0;
  int act = 0;
  bool left = false;
  List<String> text = [
    "주인님이\n너무 좋아요!",
    "뭐라고요?\n고양이가 더 좋다구요?",
    "주인님!!\n산책가요!!",
    "뛰고싶어요!!\n주인님!",
    "오늘도 함께 달려요!\n멍 멍!",
    "주인님이랑 하는\n산책이 젤 좋아요!"
  ];

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
            child: Column(
              children: [
                left
                    ? Transform.flip(
                        flipX: true,
                        child: Image.asset(
                          _currentImage,
                          width: 240.0 - (50 - y * 10),
                          fit: BoxFit.cover,
                          filterQuality: FilterQuality.none,
                        ),
                      )
                    : Image.asset(
                        _currentImage,
                        width: 240.0 - (50 - y * 10),
                        fit: BoxFit.cover,
                        filterQuality: FilterQuality.none,
                      ),
                SizedBox(height: 8),
                Row(
                  children: [
                    Image.asset(
                      "assets/dog_tier/tier_${widget.tier}.png",
                      width: 18,
                      filterQuality: FilterQuality.none,
                    ),
                    SizedBox(width: 3),
                    Text16(
                      text: widget.name,
                      bold: true,
                    ),
                  ],
                ),
              ],
            ),
          ),
          AnimatedPositioned(
              left: dx[x] - 10,
              top: dy[y] - 40,
              duration: Duration(milliseconds: 500),
              child: widget.myhome
                  ? SizedBox(
                      width: 240.0 - (50 - y * 10),
                      child: Column(
                        children: [
                          Center(
                              child: Container(
                            child: CustomPaint(
                              painter: BubblePainter(
                                  color: Colors.white.withOpacity(0.7)),
                              child: Container(
                                padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(10.0),
                                  // boxShadow: [
                                  //   BoxShadow(
                                  //     color:
                                  //         Color(0xffBFC2C8).withOpacity(0.25),
                                  //     blurRadius: 15,
                                  //     offset: Offset(0, 10),
                                  //   ),
                                  // ],
                                ),
                                child: Text16(
                                  text: text[z],
                                  bold: true,
                                ),
                              ),
                            ),
                          )),
                        ],
                      ),
                    )
                  : SizedBox())
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
      z = random.nextInt(6);
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
