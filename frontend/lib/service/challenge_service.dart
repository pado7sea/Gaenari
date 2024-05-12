import 'dart:convert';
import 'package:forsythia/models/challenges/mission.dart';
import 'package:forsythia/models/challenges/reward.dart';
import 'package:forsythia/models/challenges/reward_notice.dart';
import 'package:forsythia/models/challenges/trophy.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;

// ChallengeService 클래스
// 도전과제, 업적, 미션
class ChallengeService {
  static const String baseUrl = 'https://api.gaenari.kr/api/challenge-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 회원 업적 조회
  static Future<Trophy> fetchTrophy() async {
    return fetchGetData('achieve/trophy').then((data) => Trophy.fromJson(data));
  }

  // 회원 미션 조회
  static Future<Mission> fetchMission() async {
    return fetchGetData('achieve/mission')
        .then((data) => Mission.fromJson(data));
  }

  // 받지 않은 보상 여부 - 트루펄쓰로 반환 - 대시보드에서
  static Future<RewardNotice> fetchRewardNotice() async {
    return fetchGetData('reward/notice')
        .then((data) => RewardNotice.fromJson(data));
  }

  // 모든 보상받기 - 대시보드에서
  static Future<Reward> fetchReward() async {
    return fetchPutData('reward').then((data) => Reward.fromJson(data));
  }

  // 해당운동 기록 관련 보상받기
  static Future<Reward> fetchRewardRecord(recordId) async {
    return fetchPutData('reward/record/$recordId')
        .then((data) => Reward.fromJson(data));
  }

  // 도전과제 보상받기
  static Future<Reward> fetchRewardChallenge(challengeId) async {
    return fetchPutData('reward/challenge/$challengeId')
        .then((data) => Reward.fromJson(data));
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

  // put요청
  static Future<dynamic> fetchPutData(String endpoint) async {
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
  }
}
