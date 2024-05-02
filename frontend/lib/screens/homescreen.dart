// import 'dart:math';

// import 'package:flutter/material.dart';
// import 'package:forsythia/widgets/largeAppBar.dart';
// import 'package:forsythia/widgets/footer.dart';
// import 'package:forsythia/widgets/box.dart';
// import 'package:forsythia/widgets/button.dart';

// class HomeScreen extends StatefulWidget {
//   const HomeScreen({super.key});

//   @override
//   State<HomeScreen> createState() => _HomeScreenState();
// }

// class _HomeScreenState extends State<HomeScreen> {
//   bool active = false;

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: largeAppBar(
//         title: "메인화면",
//         sentence: "이 편지는 영국으로부터 시작되어 일주일 안에 7명에게 보내지 않을 시 굉장한 불행이 어쩌구",
//       ),
//       body: Column(
//         children: [
//           SmallButton(
//             text: !active ? '눌러보셈ㅋㅋ' : '눌러서 색바뀜',
//             onPressed: () {
//               // 버튼을 눌렀을 때 실행할 동작 작성
//               setState(() {
//                 active = !active;
//               });
//             },
//             active: active,
//           ),
//           Container(
//             decoration: myBoxDecoration,
//             child: Text("메롱ㅋㅋ"),
//           )
//         ],
//       ),
//     );
//   }
// }

// class ImageMove extends StatefulWidget {
//   @override
//   _ImageMoveState createState() => _ImageMoveState();
// }

// class _ImageMoveState extends State<ImageMove> with TickerProviderStateMixin {
//   // AnimationController를 생성
//   late AnimationController _controller;
//   // 이미지의 현재 위치를 저장하기 위한 변수
//   late Offset _position;
//   // 현재 보여지는 이미지의 경로를 저장하기 위한 변수
//   late String _currentImage;
//   // 이미지가 움직이는 중인지 여부를 나타내는 변수
//   bool _isMoving = false;

//   @override
//   void initState() {
//     super.initState();
//     // AnimationController를 초기화하고 지속시간을 500밀리초로 설정
//     _controller = AnimationController(
//       vsync: this,
//       duration: Duration(milliseconds: 500),
//     );
//     // 이미지의 초기 위치를 설정
//     _position = Offset(0.0, 0.0);
//     // 초기 이미지를 설정
//     _currentImage =
//         _isMoving == true ? 'assets/Crouched.gif' : 'assets/Sitting.gif';
//   }

//   @override
//   void dispose() {
//     // AnimationController를 dispose하여 메모리 누수를 방지
//     _controller.dispose();
//     super.dispose();
//   }

//   @override
//   Widget build(BuildContext context) {
//     return GestureDetector(
//       // GestureDetector를 사용하여 이미지를 감싼다
//       onTap: () {
//         // 이미지를 클릭할 때마다 위치를 변경한다
//         _moveImage();
//       },
//       child: Stack(
//         children: <Widget>[
//           // AnimatedPositioned를 사용하여 이미지를 위치시킨다
//           AnimatedPositioned(
//             left: _position.dx,
//             top: _position.dy,
//             duration: Duration(milliseconds: 1000),
//             child: Image.asset(
//               _currentImage,
//               width: 200.0, // 이미지 너비
//               height: 200.0, // 이미지 높이
//               fit: BoxFit.cover,
//               filterQuality: FilterQuality.none,
//             ),
//           ),
//         ],
//       ),
//     );
//   }

//   // 이미지를 클릭할 때마다 이동시키는 함수
//   void _moveImage() {
//     // 이미지가 움직이는 중이라면 함수를 종료
//     if (_isMoving) return;

//     // 이미지가 움직이는 중임을 표시
//     _isMoving = true;
//     // 랜덤한 위치를 생성
//     double screenWidth = MediaQuery.of(context).size.width;
//     double screenHeight = MediaQuery.of(context).size.height;
//     Offset newPosition = Offset(
//       _randomPosition(screenWidth),
//       _randomPosition(screenHeight),
//     );
//     // AnimationController를 이용하여 위치 변경 애니메이션을 실행
//     _controller.reset();
//     _controller.forward().then((_) {
//       // 애니메이션이 완료되면 새로운 이미지를 설정하고 이미지가 움직이는 중이 아님을 표시
//       setState(() {
//         _position = newPosition;
//         _isMoving = false;
//       });
//     });
//   }

//   // 랜덤한 위치 값을 생성하는 함수
//   double _randomPosition(double max) {
//     return Random().nextDouble() * (max - 200);
//   }
// }
