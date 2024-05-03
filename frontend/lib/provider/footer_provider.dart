import 'package:flutter/material.dart';

//바텀바에서 탭의 위치를 저장하는 프로바이더입니다.
class FooterProvider extends ChangeNotifier {
  int _index = 0;
  int get currentPage => _index; //get 함수로 외부에서 접근 가능하게 함

  // index 업데이트
  updateCurrentPage(int index) {
    _index = index; // index 값을 받아와 _index 스크린으로 변경
    notifyListeners();
  }
}
