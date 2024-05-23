import 'dart:convert';
import 'dart:developer';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

class FcmService {
  static const String baseUrl = 'https://api.gaenari.kr/api/fcm-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  Future<void> fetchFcmSave() async {
    String? fcmToken = await FirebaseMessaging.instance.getToken();
    log("Check FcmService : $fcmToken");
    String? token = await secureStorageService.getToken();
    final response = await http.post(
      Uri.parse('$baseUrl/fcm/save?fcmToken=$fcmToken'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token'
      },
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] != "SUCCESS") {
        throw Exception("status : ${data['status']}");
      }
    } else {
      throw Exception('statusCode : ${response.statusCode}');
    }
  }

  Future<void> fetchFcmDelete() async {
    String? token = await secureStorageService.getToken();
    final response = await http.delete(
      Uri.parse('$baseUrl/fcm/delete'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token'
      },
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] != "SUCCESS") {
        throw Exception("status : ${data['status']}");
      }
    } else {
      throw Exception('statusCode : ${response.statusCode}');
    }
  }
}
