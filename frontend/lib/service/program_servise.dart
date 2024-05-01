//예시임 똑같이 쓰면 조짐

import 'dart:convert';
import 'package:forsythia/models/program/programList.dart';
import 'package:forsythia/models/program/programDetail.dart';
import 'package:http/http.dart' as http;

class ProgramServise {
  static const String baseUrl = 'https://api.gaenari.kr/api/program-service/';

  static Future<programDetail> fetchProgramDetail(number) async {
    final response = await http.get(Uri.parse('$baseUrl/program/$number'),
        headers: {
          'Accept-Encoding': 'gzip',
          'Content-Type': 'application/json; charset=UTF-8'
        });

    if (response.statusCode == 200) {
      // HTTP 요청이 성공했을 때
      // JSON 데이터를 Dart 객체로 변환
      final jsonData = json.decode(response.body);
      // 변환된 데이터를 programDetail 객체로 만들어서 반환
      return programDetail.fromJson(jsonData);
    } else {
      // 데이터를 가져오는 데 실패했을 때
      throw Exception('Failed to load program detail');
    }
  }
}
