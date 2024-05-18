import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/records/record_detail.dart';
import 'package:forsythia/models/records/record_list.dart';
import 'package:forsythia/models/records/statistic_list.dart';
import 'package:forsythia/models/records/weekly_statistic_list.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

// RecordSevice 클래스
// 기록, 통계 api
class RecordSevice {
  static const String baseUrl =
      'https://api.gaenari.kr/api/exercise-record-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 월간기록 조회 - 년과 월을 파라미터로 받음
  static Future<MonthlyRecordList> fetchMonthlyRecordList(
      BuildContext context, year, month) async {
    try {
      return fetchGetData(context, 'record/month/$year/$month')
          .then((data) => MonthlyRecordList.fromJson(data));
    } catch (e) {
      print('Error fetching Monthly Record List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // // 주간기록 조회 - 현재시간의 년,월,일을 파라미터로 받음
  // static Future<RecordList> fetchWeeklyRecordList(year, month, day) async {
  //   return fetchGetData('record/week/$year/$month/$day')
  //       .then((data) => RecordList.fromJson(data));
  // }

  // 월간통계 조회 - 년과 월을 파라미터로 받음
  static Future<StatisticList> fetchMonthlyStatisticList(
      BuildContext context, year, month) async {
    try {
      return fetchGetData(context, 'statistic/month/$year/$month')
          .then((data) => StatisticList.fromJson(data));
    } catch (e) {
      print('Error fetching Monthly Statistic List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 주간통계 조회 - 현재시간의 년,월,일을 파라미터로 받음
  static Future<WeeklyStatisticList> fetchWeeklyStatisticList(
      BuildContext context, year, month, day) async {
    try {
      return fetchGetData(context, 'statistic/week/$year/$month/$day')
          .then((data) => WeeklyStatisticList.fromJson(data));
    } catch (e) {
      print('Error fetching Weekly Statistic List: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 기록 상세 - 레코드아이디를 파라미터로 받음
  static Future<DetailRecordList> fetchDetailRecordDetail(
      BuildContext context, recordId) async {
    try {
      return fetchGetData(context, 'record/$recordId')
          .then((data) => DetailRecordList.fromJson(data));
    } catch (e) {
      print('Error fetching Detail Record Detail: $e');
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
          throw Exception('Failed to load data ${data['message']}');
        }
      } else {
        throw Exception('Failed to load data ${response.reasonPhrase}');
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
}
