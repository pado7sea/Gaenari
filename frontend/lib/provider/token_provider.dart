import 'package:flutter/material.dart';

class TokenProvider extends ChangeNotifier {
  String? _token;

  String? get token => _token;

  void updateToken(String newToken) {
    _token = newToken;
    notifyListeners();
  }
}
