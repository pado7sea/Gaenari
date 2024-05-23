// ignore_for_file: use_build_context_synchronously

import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/watch/watch.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

// 워치 연동 서비스
class WatchService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 워치 인증번호 발급
  static Future<Watch> fetchWatch(BuildContext context) async {
    try {
      return fetchGetData(context, 'watch')
          .then((data) => Watch.fromJson(data));
    } catch (e) {
      print('Error fetching Watch: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // get요청
  static Future<dynamic> fetchGetData(
      BuildContext context, String endpoint) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.get(
        Uri.parse('$baseUrl/$endpoint'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token'
        },
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return data;
        } else {
          throw Exception('Failed to load data');
        }
      } else {
        throw Exception('Failed to load data');
      }
    } catch (e) {
      print('Error fetching Get Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }
}
