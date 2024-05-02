import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';

class SmallButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final bool active;

  const SmallButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.active = false,
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
        onPressed: onPressed,
        style: ElevatedButton.styleFrom(
          backgroundColor: active ? myLightGreen : myLightGrey,
          padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
        ),
        child: Text16(text: text, textColor: active ? Colors.white : myBlack));
  }
}
