import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/mates/accept_form.dart';
import 'package:forsythia/models/mates/mate_add.dart';
import 'package:forsythia/models/mates/mate_list.dart';
import 'package:forsythia/models/mates/search_mate_list.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

// MateService 클래스
// 친구 관련 서비스 전부
class MateService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 검색한 친구목록 불러오기
  static Future<SearchMateList> fetchSearchMateList(
      BuildContext context, keyword) async {
    try {
      return fetchGetData(context, 'mate/search?nickName=$keyword')
          .then((data) => SearchMateList.fromJson(data));
    } catch (e) {
      print('Error fetching Search Mate List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 요청보낸 친구목록 불러오기
  static Future<MateList> fetchSentMateList(BuildContext context) async {
    try {
      return fetchGetData(context, 'mate/list/sent')
          .then((data) => MateList.fromJson(data));
    } catch (e) {
      print('Error fetching Sent Mate List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 요청받은 친구목록 불러오기
  static Future<MateList> fetchReceivedMateList(BuildContext context) async {
    try {
      return fetchGetData(context, 'mate/list/received')
          .then((data) => MateList.fromJson(data));
    } catch (e) {
      print('Error fetching Received Mate List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 친구목록 불러오기
  static Future<MateList> fetchMateList(BuildContext context) async {
    try {
      return fetchGetData(context, 'mate')
          .then((data) => MateList.fromJson(data));
    } catch (e) {
      print('Error fetching Mate List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 친구추가하기
  static Future<MateAdd> fetchAddMate(BuildContext context, id) async {
    try {
      return fetchPostData(context, 'mate/add/$id')
          .then((data) => MateAdd.fromJson(data));
    } catch (e) {
      print('Error fetching Add Mate: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 친구삭제하기
  static Future<MateAdd> fetchDeleteMate(BuildContext context, id) async {
    try {
      return fetchPutData(context, 'mate/delete/$id')
          .then((data) => MateAdd.fromJson(data));
    } catch (e) {
      print('Error fetching Delete Mate: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 친구요청 거절/수락
  static Future<dynamic> fetchAcceptMate(
      BuildContext context, AcceptForm acceptForm) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.post(
        Uri.parse('$baseUrl/mate/check'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token'
        },
        body: json.encode(acceptForm.toJson()),
      );

      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return data;
        } else {
          throw Exception('내잘못');
        }
      } else {
        throw Exception('백잘못 ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching Accept Mate: $e');
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

  // put요청
  static Future<dynamic> fetchPutData(
      BuildContext context, String endpoint) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.put(
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
      print('Error fetching Put Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // post요청
  static Future<dynamic> fetchPostData(
      BuildContext context, String endpoint) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.post(
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
      print('Error fetching Post Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }
}
