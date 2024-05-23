import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:forsythia/models/users/login_user.dart';

class SecureStorageService {
  final _storage = FlutterSecureStorage();

  Future<void> saveToken(String accessToken) async {
    try {
      await _storage.write(key: 'accessToken', value: accessToken);
    } catch (e) {
      print('Error saving token: $e');
      rethrow;
    }
  }

  Future<void> saveLoginInfo(LoginInfo info) async {
    try {
      String jsonString = jsonEncode(info.toJson());
      await _storage.write(key: 'info', value: jsonString);
    } catch (e) {
      print('Error saving login info: $e');
      rethrow;
    }
  }

  Future<String?> getToken() async {
    try {
      return await _storage.read(key: 'accessToken');
    } catch (e) {
      print('Error getting token: $e');
      rethrow;
    }
  }

  Future<LoginInfo?> getLoginInfo() async {
    try {
      String? jsonString = await _storage.read(key: 'info');
      if (jsonString != null) {
        Map<String, dynamic> jsonMap = jsonDecode(jsonString);
        return LoginInfo.fromJson(jsonMap);
      }
      return null;
    } catch (e) {
      print('Error getting login info: $e');
      rethrow;
    }
  }

  Future<void> deleteToken() async {
    try {
      await _storage.delete(key: 'accessToken');
      await _storage.delete(key: 'info');
    } catch (e) {
      print('Error deleting token: $e');
      rethrow;
    }
  }
}
