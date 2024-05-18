import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/coins/coin_history.dart';
import 'package:forsythia/models/coins/coin_love.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

class CoinService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 회원코인내역조회
  static Future<MonthlyCoinRecord> fetchMonthlyCoinRecord(
      BuildContext context, year, month) async {
    try {
      return fetchGetData(context, 'coin/$year/$month')
          .then((data) => MonthlyCoinRecord.fromJson(data));
    } catch (e) {
      print('Error fetching Monthly Coin Record: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
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
      throw e;
    }
  }

  // 코인 증가
  static Future<CoinRes> fetchPetLove(coin) async {
    return fetchPostBodyData('coin', coin)
        .then((data) => CoinRes.fromJson(data));
  }

  // 바디있는 포스트요청
  static Future<dynamic> fetchPostBodyData(
      String endpoint, CoinLove coin) async {
    String? token = await secureStorageService.getToken();
    final response = await http.post(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token',
      },
      body: json.encode(coin.toJson()),
    );

    if (response.statusCode == 200) {
      final dynamic data = json.decode(utf8.decode(response.bodyBytes));
      if (data['status'] == "SUCCESS") {
        return data;
      } else {
        throw Exception("status : ${data['status']}");
      }
    } else {
      throw Exception('statusCode : ${response.statusCode}');
    }
  }
}
