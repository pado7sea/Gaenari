import 'package:flutter/material.dart';

final BoxDecoration myBoxDecoration = BoxDecoration(
  color: Color(0xffFFFFFF), // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 30%)
      // spreadRadius: 2, // 그림자 퍼지는 정도
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);
