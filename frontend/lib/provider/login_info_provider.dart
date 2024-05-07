// import 'package:flutter/material.dart';
// import 'package:forsythia/models/users/login_user.dart';

// class LoginInfoProvider extends ChangeNotifier {
//   LoginInfo? _loginInfo;

//   LoginInfo? get loginInfo => _loginInfo;

//   void setLoginInfo(LoginInfo info) {
//     _loginInfo = info;
//     notifyListeners();
//     print(
//         '/////////////////////////////////////////////////////////////////////////');
//     print(_loginInfo);
//   }
// }

import 'package:flutter/material.dart';
import 'package:forsythia/models/users/login_user.dart';

// 로그인 정보가 담겨 있는 프로바이더 입니다.
class LoginInfoProvider with ChangeNotifier {
  LoginInfo? _loginInfo;
  // 로그인 정보 가져오기
  LoginInfo? get loginInfo => _loginInfo;

  void updateLoginInfo(LoginInfo loginInfo) {
    _loginInfo = loginInfo;
    notifyListeners();
  }
}
