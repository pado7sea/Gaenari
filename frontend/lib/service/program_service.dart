//예시임 똑같이 쓰면 조짐

import 'dart:convert';
import 'package:forsythia/models/programs/program_detail.dart';
import 'package:forsythia/models/programs/program_list.dart';
import 'package:http/http.dart' as http;

class ProgramService {
  static const String baseUrl = 'https://api.gaenari.kr/api/program-service/';

  // 중복된 부분을 함수로 빼서 재사용하도록 함.
  static Future<dynamic> fetchData(String endpoint) async {
    final response = await http.get(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {'Content-Type': 'application/json'},
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

  static Future<ProgramDetail> fetchProgramDetail(number) async {
    return fetchData('program/$number')
        .then((data) => ProgramDetail.fromJson(data));
  }

  static Future<ProgramList> fetchProgramList() async {
    return fetchData('program').then((data) => ProgramList.fromJson(data));
  }
}
