import 'package:flutter/material.dart';

// 로그인 시에 토큰을 저장해두는 프로바이더 입니다.
class TokenProvider extends ChangeNotifier {
  String? _token;

  String? get token => _token;

  void updateToken(String newToken) {
    _token = newToken;
    notifyListeners();
  }
}
