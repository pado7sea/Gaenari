// ignore_for_file: use_build_context_synchronously

import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:forsythia/models/pet/pet_adopt.dart';
import 'package:forsythia/models/pet/pet_heart.dart';
import 'package:forsythia/models/pet/pet_res.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:http/http.dart' as http;
import 'package:forsythia/screens/login/error_screen.dart';

// 펫 서비스
class PetService {
  static const String baseUrl = 'https://api.gaenari.kr/api/member-service';
  static final SecureStorageService secureStorageService =
      SecureStorageService();

  // 강아지입양
  static Future<PetRes> fetchPetAdopt(BuildContext context, pet) async {
    try {
      return fetchPostBodyData(context, 'pet/adopt', pet)
          .then((data) => PetRes.fromJson(data));
    } catch (e) {
      print('Error fetching Pet Adopt: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 애정도 증가
  static Future<PetRes> fetchPetLove(pet) async {
    return fetchPostBodyData2('pet/heart', pet)
        .then((data) => PetRes.fromJson(data));
  }

  // 바디있는 포스트요청
  static Future<dynamic> fetchPostBodyData2(
      String endpoint, PetHeart pet) async {
    String? token = await secureStorageService.getToken();
    final response = await http.post(
      Uri.parse('$baseUrl/$endpoint'),
      headers: {
        'Content-Type': 'application/json',
        'authorization': 'Bearer $token',
      },
      body: json.encode(pet.toJson()),
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

  // 강아지변경
  static Future<PetRes> fetchPetPartner(BuildContext context, id) async {
    try {
      return fetchPutData(context, 'pet/partner/$id')
          .then((data) => PetRes.fromJson(data));
    } catch (e) {
      print('Error fetching Pet Partner: $e');
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ErrorScreen()),
      );
      rethrow;
    }
  }

  // 바디있는 포스트요청
  static Future<dynamic> fetchPostBodyData(
      BuildContext context, String endpoint, PetAdopt pet) async {
    try {
      String? token = await secureStorageService.getToken();
      final response = await http.post(
        Uri.parse('$baseUrl/$endpoint'),
        headers: {
          'Content-Type': 'application/json',
          'authorization': 'Bearer $token',
        },
        body: json.encode(pet.toJson()),
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
    } catch (e) {
      print('Error fetching Post Body Data: $e');
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
