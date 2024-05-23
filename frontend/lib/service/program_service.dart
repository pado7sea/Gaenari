// ignore_for_file: use_build_context_synchronously

import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/programs/program_add.dart';
import 'package:forsythia/models/programs/program_detail.dart';
import 'package:forsythia/models/programs/program_list.dart';
import 'package:forsythia/models/programs/program_res.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

class ProgramService {
  static const String baseUrl = 'https://api.gaenari.kr/api/program-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 운동프로그램 상세조회
  static Future<ProgramDetail> fetchProgramDetail(
      BuildContext context, int id) async {
    try {
      return fetchGetData(context, 'program/$id')
          .then((data) => ProgramDetail.fromJson(data));
    } catch (e) {
      print('Error fetching Program Detail: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 운동프로그램 리스트 불러오기
  static Future<ProgramList> fetchProgramList(BuildContext context) async {
    try {
      return fetchGetData(context, 'program')
          .then((data) => ProgramList.fromJson(data));
    } catch (e) {
      print('Error fetching Program List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 운동프로그램 삭제하기
  static Future<ProgramRes> fetchDeleteProgram(BuildContext context, id) async {
    try {
      return fetchDeleteData(context, 'program/$id')
          .then((data) => ProgramRes.fromJson(data));
    } catch (e) {
      print('Error fetching Delete Program: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 운동프로그램 즐겨찾기 등록
  static Future<ProgramResBool> fetchFavoriteProgram(
      BuildContext context, id) async {
    try {
      return fetchPutData(context, 'program/favorite/$id')
          .then((data) => ProgramResBool.fromJson(data));
    } catch (e) {
      print('Error fetching Favorite Program: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 프로그램 추가하기
  static Future<dynamic> fetchProgramAdd(
      BuildContext context, ProgramAdd program) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.post(
        Uri.parse('$baseUrl/program'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token'
        },
        body: json.encode(program.toJson()),
      );
      print(json.encode(program.toJson()));
      if (response.statusCode == 200) {
        final dynamic data = json.decode(utf8.decode(response.bodyBytes));
        if (data['status'] == "SUCCESS") {
          return data;
        } else {
          throw Exception('내잘못');
        }
      } else {
        throw Exception('백잘못: 프로그램추가안됨 : ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching Program Add: $e');
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
          throw Exception('내잘못');
        }
      } else {
        throw Exception('혜진이잘못 : 에러코드${response.statusCode}');
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

  // delete요청
  static Future<dynamic> fetchDeleteData(
      BuildContext context, String endpoint) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.delete(
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
      print('Error fetching Delete Data: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
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
      rethrow;
    }
  }
}
