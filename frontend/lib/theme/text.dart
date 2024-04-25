// ignore_for_file: use_super_parameters

import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';

// 12 크기의 텍스트
class Text12 extends StatelessWidget {
  final String text; //글씨
  final bool bold; // 굵음 여부
  final Color textColor; // 텍스트 컬러

  const Text12({
    Key? key,
    required this.text,
    this.bold = false,
    this.textColor = myBlack, // textColor의 기본값은 myBlack
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(
        fontSize: 12,
        color: textColor,
        fontWeight: bold ? FontWeight.bold : FontWeight.normal,
      ),
    );
  }
}

// 16 크기의 텍스트
class Text16 extends StatelessWidget {
  final String text; //글씨
  final bool bold; // 굵음 여부
  final Color textColor; // 텍스트 컬러

  const Text16({
    Key? key,
    required this.text,
    this.bold = false,
    this.textColor = myBlack, // textColor의 기본값은 myBlack
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(
        fontSize: 16,
        color: textColor,
        fontWeight: bold ? FontWeight.bold : FontWeight.normal,
      ),
    );
  }
}

// 20 크기의 텍스트
class Text20 extends StatelessWidget {
  final String text; //글씨
  final bool bold; // 굵음 여부
  final Color textColor; // 텍스트 컬러

  const Text20({
    Key? key,
    required this.text,
    this.bold = false,
    this.textColor = myBlack, // textColor의 기본값은 myBlack
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(
        fontSize: 20,
        color: textColor,
        fontWeight: bold ? FontWeight.bold : FontWeight.normal,
      ),
    );
  }
}

// 25 크기의 텍스트
class Text25 extends StatelessWidget {
  final String text; //글씨
  final bool bold; // 굵음 여부
  final Color textColor; // 텍스트 컬러

  const Text25({
    Key? key,
    required this.text,
    this.bold = false,
    this.textColor = myBlack, // textColor의 기본값은 myBlack
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(
        fontSize: 25,
        color: textColor,
        fontWeight: bold ? FontWeight.bold : FontWeight.normal,
      ),
    );
  }
}

// 36 크기의 텍스트
class Text36 extends StatelessWidget {
  final String text; //글씨
  final bool bold; // 굵음 여부
  final Color textColor; // 텍스트 컬러

  const Text36({
    Key? key,
    required this.text,
    this.bold = false,
    this.textColor = myBlack, // textColor의 기본값은 myBlack
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(
        fontSize: 36,
        color: textColor,
        fontWeight: bold ? FontWeight.bold : FontWeight.normal,
      ),
    );
  }
}
