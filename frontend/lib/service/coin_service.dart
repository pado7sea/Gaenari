import 'dart:convert';
import 'package:forsythia/models/coins/coin_history.dart';
import 'package:forsythia/models/coins/coin_love.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

class CoinService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 회원코인내역조회
  static Future<MonthlyCoinRecord> fetchMonthlyCoinRecord(year, month) async {
    return fetchGetData('coin/$year/$month')
        .then((data) => MonthlyCoinRecord.fromJson(data));
  }

  // get요청
  static Future<dynamic> fetchGetData(String endpoint) async {
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
