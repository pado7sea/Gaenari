import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/challenges/mission.dart';
import 'package:forsythia/models/challenges/reward.dart';
import 'package:forsythia/models/challenges/reward_notice.dart';
import 'package:forsythia/models/challenges/trophy.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

// ChallengeService 클래스
// 도전과제, 업적, 미션
class ChallengeService {
  static const String baseUrl = 'https://api.gaenari.kr/api/challenge-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 회원 업적 조회
  static Future<Trophy> fetchTrophy(BuildContext context) async {
    try {
      return fetchGetData(context, 'achieve/trophy')
          .then((data) => Trophy.fromJson(data));
    } catch (e) {
      print('Error fetching Trophy: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 회원 미션 조회
  static Future<Mission> fetchMission(BuildContext context) async {
    try {
      return fetchGetData(context, 'achieve/mission')
          .then((data) => Mission.fromJson(data));
    } catch (e) {
      print('Error fetching Mission: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 받지 않은 보상 여부 - 트루펄쓰로 반환 - 대시보드에서
  static Future<RewardNotice> fetchRewardNotice(BuildContext context) async {
    try {
      return fetchGetData(context, 'reward/notice')
          .then((data) => RewardNotice.fromJson(data));
    } catch (e) {
      print('Error fetching Reward Notice: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 모든 보상받기 - 대시보드에서
  static Future<Reward> fetchReward(BuildContext context) async {
    try {
      return fetchPutData(context, 'reward')
          .then((data) => Reward.fromJson(data));
    } catch (e) {
      print('Error fetching Reward: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 해당운동 기록 관련 보상받기
  static Future<Reward> fetchRewardRecord(
      BuildContext context, recordId) async {
    try {
      return fetchPutData(context, 'reward/record/$recordId')
          .then((data) => Reward.fromJson(data));
    } catch (e) {
      print('Error fetching Reward Record: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      throw e;
    }
  }

  // 도전과제 보상받기
  static Future<Reward> fetchRewardChallenge(
      BuildContext context, challengeId) async {
    try {
      return fetchPutData(context, 'reward/challenge/$challengeId')
          .then((data) => Reward.fromJson(data));
    } catch (e) {
      print('Error fetching Reward Challenge: $e');
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
          throw Exception(
              'Failed to load data ${response.statusCode}  ${data.toString()}');
        }
      } else {
        throw Exception('Failed to load data ${response.statusCode}');
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
}
