import 'dart:convert';
import 'package:forsythia/models/records/record_list.dart';
import 'package:forsythia/models/records/statistic_list.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// RecordSevice 클래스
// 기록, 통계 api
class RecordSevice {
  static const String baseUrl =
      'https://api.gaenari.kr/api/exercise-record-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 월간기록 조회 - 년과 월을 파라미터로 받음
  static Future<RecordList> fetchMonthlyRecordList(year, month) async {
    return fetchGetData('record/month/$year/$month')
        .then((data) => RecordList.fromJson(data));
  }

  // 주간기록 조회 - 현재시간의 년,월,일을 파라미터로 받음
  static Future<RecordList> fetchWeeklyRecordList(year, month, day) async {
    return fetchGetData('record/week/$year/$month/$day')
        .then((data) => RecordList.fromJson(data));
  }

  // 월간통계 조회 - 년과 월을 파라미터로 받음
  static Future<StatisticList> fetchMonthlyStatisticList(year, month) async {
    return fetchGetData('statistic/month/$year/$month')
        .then((data) => StatisticList.fromJson(data));
  }

  // 주간통계 조회 - 현재시간의 년,월,일을 파라미터로 받음
  static Future<StatisticList> fetchWeeklyStatisticList(
      year, month, day) async {
    return fetchGetData('statistic/week/$year/$month/$day')
        .then((data) => StatisticList.fromJson(data));
  }

  // 기록 상세 - 레코드아이디를 파라미터로 받음 - 스웨거이슈로 모델클래스 못만듦
  // static Future<RecordList> fetchRecordDetail(recordId) async {
  //   return fetchGetData('record/$recordId')
  //       .then((data) => RecordList.fromJson(data));
  // }

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
}
