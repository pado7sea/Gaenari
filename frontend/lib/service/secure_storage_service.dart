import 'dart:convert';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:forsythia/models/users/login_user.dart';

class SecureStorageService {
  final _storage = FlutterSecureStorage();

  Future<void> saveToken(String accessToken) async {
    await _storage.write(key: 'accessToken', value: accessToken);
  }

  Future<void> saveLoginInfo(LoginInfo info) async {
    String jsonString =
        jsonEncode(info.toJson()); // LoginInfo 객체를 JSON 스트링으로 변환
    await _storage.write(key: 'info', value: jsonString); // 변환한 JSON 스트링을 저장
  }

  Future<String?> getToken() async {
    return await _storage.read(key: 'accessToken');
  }

  Future<LoginInfo?> getLoginInfo() async {
    String? jsonString = await _storage.read(key: 'info'); // 저장된 JSON 스트링 읽어오기
    if (jsonString != null) {
      Map<String, dynamic> jsonMap = jsonDecode(jsonString); // JSON 스트링을 맵으로 변환
      return LoginInfo.fromJson(jsonMap); // 맵을 LoginInfo 객체로 변환해서 반환
    }
    return null; // 만약 아무것도 저장되어 있지 않으면 null 반환
  }

  Future<void> deleteToken() async {
    //삭제
    await _storage.delete(key: 'accessToken');
    await _storage.delete(key: 'info');
  }
}
