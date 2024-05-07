import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';

class SmallButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final bool active;
  final double widthPadding;
  final double heightPadding;

  const SmallButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.active = false,
    this.widthPadding = 20,
    this.heightPadding = 12,
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
        onPressed: onPressed,
        style: ElevatedButton.styleFrom(
          backgroundColor: active ? myLightGreen : myLightGrey,
          padding: EdgeInsets.symmetric(
              horizontal: widthPadding, vertical: heightPadding),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10),
          ),
        ),
        child: SizedBox(
            child: Text16(
                text: text, textColor: active ? Colors.white : myBlack)));
  }
}
