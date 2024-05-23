import 'package:flutter/material.dart';
import 'package:forsythia/models/users/signup_user.dart';

// 회원가입시에 작성한 정보를 저장해두는 프로바이더 입니다.
class SignupProvider with ChangeNotifier {
  final SignupUser _user = SignupUser();
  final MyPet _pet = MyPet();

  SignupUser get user => _user;
  MyPet get pet => _pet;

  // 이메일 설정
  void setaccountId(String accountId) {
    _user.accountId = accountId;
    notifyListeners();
  }

  // 비밀번호 설정
  void setPassword(String password) {
    _user.password = password;
    notifyListeners();
  }

  // 닉네임 설정
  void setNickName(String nickName) {
    _user.nickName = nickName;
    notifyListeners();
  }

  // 생년월일 설정
  void setBirth(String birth) {
    _user.birth = birth;
    notifyListeners();
  }

  // 성별 설정
  void setGender(String gender) {
    _user.gender = gender;
    notifyListeners();
  }

  // 키 설정
  void setHeight(int height) {
    _user.height = height;
    notifyListeners();
  }

  // 몸무게 설정
  void setWeight(int weight) {
    _user.weight = weight;
    notifyListeners();
  }

  // 펫 정보 설정
  void setPet(int id, String name) {
    _user.myPet = MyPet(id: id, name: name);
    notifyListeners();
  }
}
