import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';

final BoxDecoration myBoxDecoration = BoxDecoration(
  color: Color(0xffFFFFFF), // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);
final BoxDecoration myRedBoxDecoration = BoxDecoration(
  color: myLightRed, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);
final BoxDecoration myNoneRedBoxDecoration = BoxDecoration(
  color: myLightRed, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
);
final BoxDecoration myYellowBoxDecoration = BoxDecoration(
  color: myLightYellow, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);
final BoxDecoration myNoneYellowBoxDecoration = BoxDecoration(
  color: myLightYellow, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
);
final BoxDecoration myGreenBoxDecoration = BoxDecoration(
  color: myWhiteGreen, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  // boxShadow: [
  //   BoxShadow(
  //     color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
  //     blurRadius: 15, // 그림자 흐림 정도
  //     offset: Offset(0, 10), // 그림자의 위치 (x, y)
  //   ),
  // ],
);
final BoxDecoration myLightGreenBoxDecoration = BoxDecoration(
  color: myLightGreen, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);
final BoxDecoration myActiveBoxDecoration = BoxDecoration(
  color: myMainGreen, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);
final BoxDecoration myNoneBoxDecoration = BoxDecoration(
  color: myLightGrey, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);

final BoxDecoration myBorderBoxDecoration = BoxDecoration(
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
  border: Border.all(color: myLightGreen, width: 1),
);
final BoxDecoration myBlackBorderBoxDecoration = BoxDecoration(
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
  border: Border.all(color: myLightGreen, width: 2),
);

final BoxDecoration myYellowBorderBoxDecoration = BoxDecoration(
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
  border: Border.all(color: myYellow, width: 1),
);

final BoxDecoration myTrophyBoxDecoration = BoxDecoration(
  color: Color(0xffFFFFFF), // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  border: Border.all(color: myYellow, width: 1),
);

final BoxDecoration myWalkBoxDecoration = BoxDecoration(
  color: myLightYellow, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);
final BoxDecoration myRunBoxDecoration = BoxDecoration(
  color: myWhiteGreen, // 배경색
  borderRadius: BorderRadius.circular(15), // 박스의 모서리를 둥글게
  boxShadow: [
    BoxShadow(
      color: Color(0xffBFC2C8).withOpacity(0.25), // 그림자 색 (투명도 25%)
      blurRadius: 15, // 그림자 흐림 정도
      offset: Offset(0, 10), // 그림자의 위치 (x, y)
    ),
  ],
);

final Container myContainer = Container(
  decoration: myBoxDecoration,
);
