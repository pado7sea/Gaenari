//예시임 똑같이 쓰면 조짐

import 'dart:convert';
import 'package:forsythia/models/program/programDetail.dart';
import 'package:forsythia/models/program/programList.dart';
import 'package:http/http.dart' as http;

class ProgramService {
  static const String baseUrl = 'https://api.gaenari.kr/api/program-service/';

  static Future<ProgramDetail> fetchProgramDetail(number) async {
    final response =
        await http.get(Uri.parse('$baseUrl/program/$number'), headers: {
      'Content-Type': 'application/json',
    });

    if (response.statusCode == 200) {
      final ProgramDetail programDetail =
          ProgramDetailFromJson(utf8.decode(response.bodyBytes));
      if (programDetail.status == "SUCCESS") {
        return programDetail;
      } else {
        throw Exception('Failed to load program detail');
      }
    } else {
      // 데이터를 가져오는 데 실패했을 때
      throw Exception('Failed to load program detail');
    }
  }

  static Future<ProgramList> fetchProgramList() async {
    final response = await http.get(Uri.parse('$baseUrl/program'), headers: {
      'Content-Type': 'application/json',
    });

    if (response.statusCode == 200) {
      final ProgramList programList =
          ProgramListFromJson(utf8.decode(response.bodyBytes));
      if (programList.status == "SUCCESS") {
        return programList;
      } else {
        throw Exception('Failed to load program list');
      }
    } else {
      throw Exception('Failed to load program list');
    }
  }
}
